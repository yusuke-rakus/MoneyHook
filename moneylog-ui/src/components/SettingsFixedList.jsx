import React, { useEffect, useState } from "react";
import "./components_CSS/SettingsFixedList.css";
import { rootURI } from "../env/env";
import SettingsFixed from "./SettingsFixed";
import { useCookies } from "react-cookie";
import { SettingsFetchError } from "./FetchError";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { Button, CircularProgress, IconButton } from "@mui/material";

const SettingsFixedList = (props) => {
  const { banner, setBanner } = props;
  const [cookie] = useCookies();
  /** 固定費の編集 */
  const [monthlyTransactionList, setMonthlyTransactionList] = useState([]);
  const [isLoading, setLoading] = useState(false);

  /** 固定費データ入力欄の追加 */
  const AddFixedDataInput = () => {
    setMonthlyTransactionList([
      ...monthlyTransactionList,
      {
        monthlyTransactionId: null,
        monthlyTransactionName: null,
        monthlyTransactionAmount: null,
        monthlyTransactionSign: -1,
        monthlyTransactionDate: null,
        categoryName: null,
        subCategoryName: null,
        label: {
          monthlyTransactionName: { message: "取引名", status: false },
          monthlyTransactionAmount: { message: "金額", status: false },
          category: { message: "カテゴリ", status: false },
          subCategory: { message: "サブカテゴリ", status: false },
        },
      },
    ]);
  };

  /** 登録処理 */
  const register = () => {
    let error = 0;

    /** バリデーションチェック */
    // 何も入力されていないデータは削除する
    setMonthlyTransactionList(
      monthlyTransactionList.filter(
        (data) =>
          !!data.categoryId ||
          !!data.subCategoryId ||
          !!data.monthlyTransactionName ||
          !!data.monthlyTransactionAmount
      )
    );

    monthlyTransactionList.forEach((data) => {
      // 未入力チェック
      if (
        !data.categoryId ||
        !data.subCategoryId ||
        !data.monthlyTransactionName ||
        !data.monthlyTransactionAmount
      ) {
        data.label = {
          monthlyTransactionName: {
            message: !data.monthlyTransactionName ? "未入力" : "取引名",
            status: !data.monthlyTransactionName,
          },
          monthlyTransactionAmount: {
            message: !data.monthlyTransactionAmount ? "未入力" : "金額",
            status: !data.monthlyTransactionAmount,
          },
          category: {
            message: !data.categoryId ? "未入力" : "カテゴリ",
            status: !data.categoryId,
          },
          subCategory: {
            message: !data.subCategoryId ? "未入力" : "サブカテゴリ",
            status: !data.subCategoryId,
          },
        };
        error++;
      } else {
        data.label = {
          monthlyTransactionName: {
            message: !data.monthlyTransactionName ? "未入力" : "取引名",
            status: !data.monthlyTransactionName,
          },
          monthlyTransactionAmount: {
            message: !data.monthlyTransactionAmount ? "未入力" : "金額",
            status: !data.monthlyTransactionAmount,
          },
          category: {
            message: !data.categoryId ? "未入力" : "カテゴリ",
            status: !data.categoryId,
          },
          subCategory: {
            message: !data.subCategoryId ? "未入力" : "サブカテゴリ",
            status: !data.subCategoryId,
          },
        };
      }

      // 取引名長さチェック
      if (
        !!data.monthlyTransactionName &&
        data.monthlyTransactionName.length > 32
      ) {
        data.label = {
          ...data.label,
          monthlyTransactionName: {
            message:
              data.monthlyTransactionName.length > 32 ? "32字以内" : "取引名",
            status: data.monthlyTransactionName.length > 32,
          },
        };
        error++;
      }

      // 金額上限チェック
      if (data.monthlyTransactionAmount > 9999999) {
        data.label = {
          ...data.label,
          monthlyTransactionAmount: {
            message:
              data.monthlyTransactionAmount > 9999999
                ? "¥9,999,999以内"
                : "金額",
            status: data.monthlyTransactionAmount > 9999999,
          },
        };
        error++;
      }
    });

    if (error > 0) {
      return;
    }

    setBanner({
      ...banner,
      banner: false,
    });
    setLoading(true);
    fetch(`${rootURI}/fixed/editFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        monthlyTransactionList: monthlyTransactionList,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功処理
        } else {
          // 失敗処理
        }
        setBanner({
          ...banner,
          bannerMessage: data.message,
          bannerType: data.status,
          banner: true,
        });
      })
      .finally(() => {
        setLoading(false);
        getInit();
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  /** リセット */
  const cancel = () => {
    setMonthlyTransactionList([]);
    getInit();
  };

  /** API関連 */
  // ユーザー情報の取得
  const getInit = () => {
    setLoading(true);
    setMonthlyTransactionList([]);
    fetch(`${rootURI}/fixed/getFixed`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          data.monthlyTransactionList.forEach((data) => {
            data["label"] = {
              monthlyTransactionName: { message: "取引名", status: false },
              monthlyTransactionAmount: { message: "金額", status: false },
              category: { message: "カテゴリ", status: false },
              subCategory: { message: "サブカテゴリ", status: false },
            };
          });
          setMonthlyTransactionList(data.monthlyTransactionList);
        } else {
          setMonthlyTransactionList([]);
        }
      })
      .finally(() => {
        setLoading(false);
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  useEffect(() => {
    getInit();
  }, [setMonthlyTransactionList]);

  return (
    <div className="containerBox">
      <p className="settingsTitle">固定費の編集</p>
      <hr className="border" />

      {/* 月次費用 */}
      {isLoading ? (
        <div className="fixedListArea">
          <div className="monthlyTransactionLoading loading"></div>
          <div className="monthlyTransactionLoading loading"></div>
          <div className="monthlyTransactionLoading loading"></div>
          <div className="monthlyTransactionLoading loading"></div>
          <div className="monthlyTransactionLoading loading"></div>
        </div>
      ) : (
        <div className="fixedListArea">
          {isLoading === false && monthlyTransactionList.length === 0 && (
            <div className="monthlyTransactionNotFound">
              <p>データが登録されていません</p>
              <p>毎月自動登録されるデータを指定して入力の手間を省きましょう</p>
            </div>
          )}

          {monthlyTransactionList.map((data, i) => {
            return (
              <SettingsFixed
                key={i}
                data={data}
                index={i}
                monthlyTransactionList={monthlyTransactionList}
                setMonthlyTransactionList={setMonthlyTransactionList}
                getInit={getInit}
                isLoading={isLoading}
                setLoading={setLoading}
                banner={banner}
                setBanner={setBanner}
              />
            );
          })}
        </div>
      )}

      {/* 追加ボタン */}
      <div className="addArea" onClick={AddFixedDataInput}>
        <IconButton>
          <AddCircleIcon />
        </IconButton>
      </div>

      <div className="fixedSettingsButtons">
        {/* リセットボタン */}
        <Button
          onClick={cancel}
          variant="contained"
          color="inherit"
          disabled={isLoading}
        >
          リセット
        </Button>

        {/* 登録ボタン */}
        <Button onClick={register} variant="contained" disabled={isLoading}>
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>
      </div>
    </div>
  );
};

export default SettingsFixedList;
