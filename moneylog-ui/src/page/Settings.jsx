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
      categoryName: "住宅",
      subCategoryName: "家賃",
    },
    {
      monthlyTransactionId: 2,
      monthlyTransactionName: "電気代",
      monthlyTransactionAmount: -7000,
      monthlyTransactionDate: 30,
      categoryName: "水道光熱費",
      subCategoryName: "なし",
    },
    {
      monthlyTransactionId: 3,
      monthlyTransactionName: "給与",
      monthlyTransactionAmount: 200000,
      monthlyTransactionDate: 25,
      categoryName: "収入",
      subCategoryName: "給与",
    },
  ]);

  const AddFixedDataInput = (e) => {
    setMonthlyTransactionList([
      ...monthlyTransactionList,
      {
        monthlyTransactionId: null,
        monthlyTransactionName: null,
        monthlyTransactionAmount: null,
        monthlyTransactionDate: null,
        categoryName: null,
        subCategoryName: null,
      },
    ]);
  };

  return (
    <div className="container">
      {/* ユーザー設定変更 */}
      <div className="containerBox">
        <p className="settingsTitle">ユーザー設定変更</p>
        <hr className="border" />
        <div className="emailBox">
          <span>メールアドレス</span>
          <TextField
            defaultValue={email}
            id="standardBasic"
            variant="standard"
            fullWidth={true}
          />
        </div>

        <div className="userSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
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
          <TextField
            id="standardBasic"
            variant="standard"
            fullWidth={true}
            type="password"
          />
        </div>
        <div className="passwordBox">
          <span>変更後のパスワード</span>
          <TextField
            id="standardBasic"
            variant="standard"
            fullWidth={true}
            type="password"
          />
        </div>
        <div className="passwordBox">
          <span>再入力</span>
          <TextField
            id="standardBasic"
            variant="standard"
            fullWidth={true}
            type="password"
          />
        </div>

        <div className="passwordSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
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
          {monthlyTransactionList.map((data) => {
            return (
              <>
                <SettingsFixed data={data} />
              </>
            );
          })}
        </div>

        <div className="addArea" onClick={AddFixedDataInput}>
          <IconButton>
            <AddCircleIcon />
          </IconButton>
        </div>

        <div className="fixedSettingsButtons">
          <Button variant="contained" color="inherit" sx={"color:#757575"}>
            キャンセル
          </Button>
          <Button variant="contained">登録</Button>
        </div>
      </div>
    </div>
  );
};
export default Settings;
