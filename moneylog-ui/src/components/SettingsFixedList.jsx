import { Button, CircularProgress, IconButton } from "@mui/material";
import React, { useEffect, useState } from "react";
import SettingsFixed from "./SettingsFixed";
import AddCircleIcon from "@mui/icons-material/AddCircle";

const SettingsFixedList = () => {
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
      })
      .finally(() => {
        setLoading(false);
        getInit();
      });
  };

  /** キャンセル */
  const cancel = () => {
    getInit();
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // ユーザー情報の取得
  const getInit = () => {
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
        }
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
            />
          );
        })}
      </div>

      <div className="addArea" onClick={AddFixedDataInput}>
        <IconButton>
          <AddCircleIcon />
        </IconButton>
      </div>

      <div className="fixedSettingsButtons">
        <Button
          onClick={cancel}
          variant="contained"
          color="inherit"
          disabled={isLoading}
        >
          キャンセル
        </Button>
        <Button onClick={register} variant="contained" disabled={isLoading}>
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>
      </div>
    </div>
  );
};

export default SettingsFixedList;
