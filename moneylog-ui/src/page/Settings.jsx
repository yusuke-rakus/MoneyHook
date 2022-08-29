import React, { useState } from "react";
import "./page_CSS/Settings.css";
import "./page_CSS/common.css";
import { TextField, Button, IconButton } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import SettingsFixed from "../components/SettingsFixed";

const Settings = () => {
  const [email, setEmail] = useState("sample@sample.com");

  const AddFixedDataInput = (e) => {
    console.log("hej");
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
        <div className="fixedListArea">
          <SettingsFixed />
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
