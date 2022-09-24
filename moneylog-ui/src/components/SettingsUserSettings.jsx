import { Button, CircularProgress, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";

const SettingsUserSettings = (props) => {
  const { banner, setBanner } = props;
  /** ユーザー設定変更 */
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setLoading] = useState(false);

  /** メールアドレス変更 */
  const changeEmail = () => {
    changeEmailApi(email, password);
  };

  /** キャンセル */
  const cansel = () => {
    getInit();
    setPassword("");
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // ユーザー情報の取得
  const getInit = () => {
    fetch(`${rootURI}/user/getUserInfo`, {
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
          setEmail(data.userInfo.email);
        }
      });
  };

  // メールアドレス変更
  const changeEmailApi = (email, password) => {
    setLoading(true);
    fetch(`${rootURI}/user/changeEmail`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        email: email,
        password: password,
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
        setEmail("");
        setPassword("");
        getInit();
        setLoading(false);
      });
  };

  useEffect(() => {
    getInit();
  }, [setEmail]);

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

      <div className="emailBox">
        <span>パスワード</span>
        <TextField
          variant="standard"
          type="password"
          autoComplete="off"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          fullWidth={true}
        />
      </div>

      <div className="userSettingsButtons">
        <Button
          onClick={cansel}
          variant="contained"
          color="inherit"
          disabled={isLoading}
        >
          キャンセル
        </Button>
        <Button onClick={changeEmail} variant="contained" disabled={isLoading}>
          {isLoading ? <CircularProgress size={20} /> : "登録"}
        </Button>
      </div>
    </div>
  );
};

export default SettingsUserSettings;
