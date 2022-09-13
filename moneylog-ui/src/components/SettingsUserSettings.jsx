import { Button, TextField } from "@mui/material";
import React, { useState } from "react";

const SettingsUserSettings = () => {
  /** ユーザー設定変更 */
  const [email, setEmail] = useState("sample@sample.com");

  /** メールアドレス変更 */
  const changeEmail = () => {
    // メールアドレス変更APIを実施する
    console.log(email);
  };

  /** キャンセル */
  const cansel = () => {
    // もとに戻す
    console.log(email);
  };

  return (
    <div className="containerBox">
      <p className="settingsTitle">ユーザー設定変更</p>
      <hr className="border" />
      <div className="emailBox">
        <span>メールアドレス</span>
        <TextField
          variant="standard"
          autoComplete="off"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          fullWidth={true}
          inputProps={{
            style: {
              color: "#424242",
            },
          }}
        />
      </div>

      <div className="userSettingsButtons">
        <Button onClick={cansel} variant="contained" color="inherit">
          キャンセル
        </Button>
        <Button onClick={changeEmail} variant="contained">
          登録
        </Button>
      </div>
    </div>
  );
};

export default SettingsUserSettings;
