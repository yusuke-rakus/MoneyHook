import { Button, CircularProgress, IconButton } from "@mui/material";
import React, { useEffect, useState } from "react";
import "./components_CSS/SettingsFixedList.css";
import SettingsFixed from "./SettingsFixed";
import AddCircleIcon from "@mui/icons-material/AddCircle";

const SettingsFixedList = (props) => {
  const { banner, setBanner } = props;
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
      },
    ]);
  };

  /** 登録処理 */
  const register = () => {
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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        monthlyTransactionList: monthlyTransactionList,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
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
      });
  };

  /** リセット */
  const cancel = () => {
    setMonthlyTransactionList([]);
    getInit();
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

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
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setMonthlyTransactionList(data.monthlyTransactionList);
        } else {
          setMonthlyTransactionList([]);
        }
      })
      .finally(() => {
        setLoading(false);
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
          {monthlyTransactionList.map((data, i) => {
            return (
              <SettingsFixed
                key={i}
                data={data}
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
