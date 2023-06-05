import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/AddTransactionListWindow.css";
/** 自作コンポーネント */
import { rootURI } from "../../env/env";
import TransactionListData from "../TransactionListData";
import { LoadFetchError } from "../FetchError";
import { getJST } from "../GetJST";
/** 外部コンポーネント */
import {
  Button,
  CircularProgress,
  IconButton,
  Collapse,
  Alert,
} from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import CloseIcon from "@mui/icons-material/Close";
import { useEffect } from "react";
import { useCookies } from "react-cookie";

const AddTransactionListWindow = (props) => {
  const [isLoading, setLoading] = useState(false);
  const {
    transactionList,
    setTransactionList,
    openWindow,
    getInit,
    month,
    setBanner,
    setBannerMessage,
    setBannerType,
  } = props;
  const [cookie] = useCookies();

  /** ウィンドウを閉じる */
  const closeModalWindow = () => {
    setTransactionList([]);
    openWindow(false);
  };

  /** リスト追加処理 */
  const appendTransactionList = () => {
    setTransactionList([
      ...transactionList,
      {
        transactionDate: getJST(new Date()),
        transactionAmount: "",
        transactionSign: -1,
        transactionName: "",
        categoryId: "",
        subCategoryId: "",
        fixedFlg: false,
        labels: {
          transactionDate: { status: false, message: "取引日" },
          categoryId: { status: false, message: "カテゴリ" },
          subCategoryId: { status: false, message: "サブカテゴリ" },
          transactionName: { status: false, message: "取引名" },
          transactionAmount: { status: false, message: "金額" },
        },
      },
    ]);
  };

  /** ウィンドウバナー */
  const [windowBanner, setWindowBanner] = useState({
    banner: false,
    bannerMessage: "",
    bannerType: "",
  });

  /** 登録処理 */
  const submitTransactionList = () => {
    let error = 0;

    /** バリデーションチェック */
    // 何も入力されていないデータは削除する
    setTransactionList(
      transactionList.filter(
        (data) =>
          !!data.categoryId ||
          !!data.subCategoryId ||
          !!data.transactionName ||
          !!data.transactionAmount
      )
    );

    transactionList.forEach((data) => {
      //未入力チェック
      if (
        !data.categoryId ||
        !data.subCategoryId ||
        !data.transactionName ||
        !data.transactionAmount
      ) {
        data.labels = {
          transactionDate: {
            message: !data.transactionDate ? "未入力" : "取引日",
            status: !data.transactionDate,
          },
          transactionName: {
            message: !data.transactionName ? "未入力" : "取引名",
            status: !data.transactionName,
          },
          transactionAmount: {
            message: !data.transactionAmount ? "未入力" : "金額",
            status: !data.transactionAmount,
          },
          categoryId: {
            message: !data.categoryId ? "未入力" : "カテゴリ",
            status: !data.categoryId,
          },
          subCategoryId: {
            message: !data.subCategoryId ? "未入力" : "サブカテゴリ",
            status: !data.subCategoryId,
          },
        };

        error++;
      } else {
        data.labels = {
          transactionDate: {
            message: !data.transactionDate ? "未入力" : "取引日",
            status: !data.transactionDate,
          },
          transactionName: {
            message: !data.transactionName ? "未入力" : "取引名",
            status: !data.transactionName,
          },
          transactionAmount: {
            message: !data.transactionAmount ? "未入力" : "金額",
            status: !data.transactionAmount,
          },
          categoryId: {
            message: !data.categoryId ? "未入力" : "カテゴリ",
            status: !data.categoryId,
          },
          subCategoryId: {
            message: !data.subCategoryId ? "未入力" : "サブカテゴリ",
            status: !data.subCategoryId,
          },
        };
      }

      // 取引名長さチェック
      if (!!data.transactionName && data.transactionName.length > 32) {
        data.labels = {
          ...data.labels,
          transactionName: {
            message: data.transactionName.length > 32 ? "32字以内" : "取引名",
            status: data.transactionName.length > 32,
          },
        };
        error++;
      }

      // 金額上限チェック
      if (data.transactionAmount > 9999999) {
        data.labels = {
          ...data.labels,
          transactionAmount: {
            message:
              data.transactionAmount > 9999999 ? "¥9,999,999以内" : "金額",
            status: data.transactionAmount > 9999999,
          },
        };
        error++;
      }
    });

    if (error > 0) {
      return;
    }
    addTransactionList();
  };

  /** 登録メソッド */
  const addTransactionList = () => {
    setLoading(true);
    setBanner(false);
    fetch(`${rootURI}/transaction/addTransactionList`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        transactionList: transactionList,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功
          closeModalWindow();
          setBanner(true);
          setBannerMessage(data.message);
          setBannerType(data.status);
          getInit(month);
        } else {
          // 失敗
          setWindowBanner({
            banner: true,
            bannerMessage: data.message,
            bannerType: data.status,
          });
        }
      })
      .finally(() => {
        setLoading(false);
      })
      .catch(() => {
        LoadFetchError(setLoading, setWindowBanner);
        closeModalWindow();
      });
  };

  useEffect(() => {
    appendTransactionList();
  }, [setTransactionList]);

  return (
    <div className="addTransactionListWindow">
      {/* バーナー */}
      <div className="windowBanner">
        <Collapse in={windowBanner.banner}>
          <Alert
            severity={windowBanner.bannerType}
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setWindowBanner({ ...windowBanner, banner: false });
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
          >
            {windowBanner.bannerMessage}
          </Alert>
        </Collapse>
      </div>

      <CloseIcon
        onClick={closeModalWindow}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />
      <h3 className="modal-title">収支の一括入力</h3>

      <div className="transactionListArea">
        {transactionList.map((data, i) => {
          return (
            <TransactionListData
              key={i}
              index={i}
              transaction={data}
              transactionList={transactionList}
              setTransactionList={setTransactionList}
            />
          );
        })}

        {/* 追加ボタン */}
        <div className="addArea">
          <IconButton onClick={appendTransactionList}>
            <AddCircleIcon />
          </IconButton>
        </div>
      </div>

      {/* 登録ボタン */}
      <Button
        onClick={submitTransactionList}
        variant="contained"
        disabled={isLoading}
        sx={{ mt: 1 }}
      >
        {isLoading ? <CircularProgress size={20} /> : "登録"}
      </Button>
    </div>
  );
};

export default AddTransactionListWindow;
