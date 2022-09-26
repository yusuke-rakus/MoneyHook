import { Button, CircularProgress, TextField } from "@mui/material";
import React from "react";
import { useState } from "react";

const SettingsChangePassword = (props) => {
  const { banner, setBanner } = props;
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword1, setNewPassword1] = useState("");
  const [newPassword2, setNewPassword2] = useState("");

  const [matchError, setMatchError] = useState(false);
  const [isLoading, setLoading] = useState(false);

  /** パスワード変更 */
  const changePassword = () => {
    // パスワード1と2が一致しているか確認
    if (newPassword1 == newPassword2) {
      // API通信
      changePasswordApi(currentPassword, newPassword1);
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

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // パスワード変更
  const changePasswordApi = (password, newPassword) => {
    setBanner({
      ...banner,
      banner: false,
    });
    setLoading(true);
    fetch(`${rootURI}/user/changePassword`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        password: password,
        newPassword: newPassword,
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
        setCurrentPassword("");
        setNewPassword1("");
        setNewPassword2("");
        setLoading(false);
      });
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
          // type="password"
          fullWidth={true}
        />
      </div>
      <div className="passwordBox">
        <span>変更後のパスワード</span>
        <TextField
          value={newPassword1}
          onChange={(e) => setNewPassword1(e.target.value)}
          variant="standard"
          // type="password"
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
          // type="password"
          fullWidth={true}
          error={matchError && matchError}
        />
      </div>

      <div className="passwordSettingsButtons">
        <Button
          onClick={cancel}
          variant="contained"
          color="inherit"
          disabled={isLoading}
        >
          キャンセル
        </Button>
        <Button
          onClick={changePassword}
          variant="contained"
          disabled={isLoading}
        >
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>
      </div>
    </div>
  );
};

export default SettingsChangePassword;
