import React, { useState } from "react";
/** CSS */
import "./page_CSS/Settings.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SettingsFixed from "../components/SettingsFixed";
/** 外部コンポーネント */
import { TextField, Button, IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";

const Settings = () => {
  const [email, setEmail] = useState("sample@sample.com");

  const [monthlyTransactionList, setMonthlyTransactionList] = useState([
    {
      monthlyTransactionId: 1,
      monthlyTransactionName: "家賃",
      monthlyTransactionAmount: -70000,
      monthlyTransactionDate: 25,
      categoryId: "1",
      categoryName: "住宅",
      subCategoryId: "1",
      subCategoryName: "家賃",
    },
    {
      monthlyTransactionId: 2,
      monthlyTransactionName: "電気代",
      monthlyTransactionAmount: -7000,
      monthlyTransactionDate: 30,
      categoryId: "2",
      categoryName: "水道光熱費",
      subCategoryId: "2",
      subCategoryName: "なし",
    },
    {
      monthlyTransactionId: 3,
      monthlyTransactionName: "給与",
      monthlyTransactionAmount: 200000,
      monthlyTransactionDate: 25,
      categoryId: "3",
      categoryName: "収入",
      subCategoryId: "3",
      subCategoryName: "給与",
    },
  ]);

  const AddFixedDataInput = () => {
    let mtId = monthlyTransactionList.slice(-1)[0].monthlyTransactionId + 1;
    setMonthlyTransactionList([
      ...monthlyTransactionList,
      {
        monthlyTransactionId: mtId,
        monthlyTransactionName: null,
        monthlyTransactionAmount: null,
        monthlyTransactionDate: null,
        categoryName: null,
        subCategoryName: null,
      },
    ]);
  };

  /** 固定費の編集画面での登録ボタン押下処理 */
  const monthlyTransactionRegister = () => {
    monthlyTransactionList.map((data) => {
      console.log(data);
    });
  };

  return (
    <div className="container">
      {/* ユーザー設定変更 */}
      <div className="containerBox">
        <p className="settingsTitle">ユーザー設定変更</p>
        <hr className="border" />
        <div className="emailBox">
          <span>メールアドレス</span>
          <TextField value={email} variant="standard" fullWidth={true} />
        </div>

        <div className="userSettingsButtons">
          <Button variant="contained" color="inherit">
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* パスワード変更 */}
      <div className="containerBox">
        <p className="settingsTitle">パスワード変更</p>
        <hr className="border" />
        <div className="passwordBox">
          <span>現在のパスワード</span>
          <TextField variant="standard" fullWidth={true} type="password" />
        </div>
        <div className="passwordBox">
          <span>変更後のパスワード</span>
          <TextField variant="standard" fullWidth={true} type="password" />
        </div>
        <div className="passwordBox">
          <span>再入力</span>
          <TextField variant="standard" fullWidth={true} type="password" />
        </div>

        <div className="passwordSettingsButtons">
          <Button variant="contained" color="inherit">
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>

      {/* 固定費の編集 */}
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
          <Button variant="contained" color="inherit">
            キャンセル
          </Button>
          <Button onClick={monthlyTransactionRegister} variant="contained">
            登録
          </Button>
        </div>
      </div>
    </div>
  );
};
export default Settings;
