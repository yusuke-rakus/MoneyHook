import { Button, TextField } from "@mui/material";
import React from "react";
import { useState } from "react";

const SettingsChangePassword = () => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword1, setNewPassword1] = useState("");
  const [newPassword2, setNewPassword2] = useState("");

  const [matchError, setMatchError] = useState(false);

  /** 登録処理 */
  const changePassword = () => {
    // パスワード1と2が一致しているか確認
    if (newPassword1 == newPassword2) {
      // API通信
      console.log("OK");
    } else {
      setMatchError(true);
    }
  };

  /** キャンセル */
  const cancel = () => {
    // 入力値をクリア
    setCurrentPassword("");
    setNewPassword1("");
    setNewPassword2("");
    // エラーを解消
    setMatchError(false);
  };

  return (
    <div className="containerBox">
      <p className="settingsTitle">パスワード変更</p>
      <hr className="border" />
      <div className="passwordBox">
        <span>現在のパスワード</span>
        <TextField
          value={currentPassword}
          onChange={(e) => setCurrentPassword(e.target.value)}
          variant="standard"
          type="password"
          fullWidth={true}
        />
      </div>
      <div className="passwordBox">
        <span>変更後のパスワード</span>
        <TextField
          value={newPassword1}
          onChange={(e) => setNewPassword1(e.target.value)}
          variant="standard"
          type="password"
          fullWidth={true}
          error={matchError && matchError}
        />
      </div>
      <div className="passwordBox">
        <span>再入力</span>
        <TextField
          value={newPassword2}
          onChange={(e) => setNewPassword2(e.target.value)}
          variant="standard"
          type="password"
          fullWidth={true}
          error={matchError && matchError}
        />
      </div>

      <div className="passwordSettingsButtons">
        <Button onClick={cancel} variant="contained" color="inherit">
          キャンセル
        </Button>
        <Button onClick={changePassword} variant="contained">
          登録
        </Button>
      </div>
    </div>
  );
};

export default SettingsChangePassword;