import { Button, CircularProgress, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";

const SettingsUserSettings = (props) => {
  const { banner, setBanner } = props;
  /** ユーザー設定変更 */
  const [email, setEmail] = useState({ value: "", message: "", error: false });
  const [password, setPassword] = useState({
    value: "",
    message: "",
    error: false,
  });
  const [isLoading, setLoading] = useState(false);

  /** メールアドレス変更 */
  const changeEmail = () => {
    // メールアドレス入力チェック
    if (!email.value) {
      setEmail((v) => ({
        ...v,
        value: email.value,
        message: "メールアドレス未入力",
        error: true,
      }));
      return;
    }
    // パスワード入力チェック
    if (!password.value) {
      setPassword((v) => ({
        ...v,
        value: password.value,
        message: "パスワード未入力",
        error: true,
      }));
      return;
    }

    // メールアドレス要件チェック
    const regex =
      /^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$/;
    if (!regex.test(email.value)) {
      setEmail((v) => ({
        ...v,
        value: email.value,
        message: "形式不一致",
        error: true,
      }));
      return;
    }

    changeEmailApi(email.value, password.value);
  };

  /** キャンセル */
  const cansel = () => {
    getInit();
    setPassword((v) => ({
      ...v,
      value: "",
      message: "",
      error: false,
    }));
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
          setEmail((v) => ({
            ...v,
            value: data.userInfo.email,
            message: "",
            error: false,
          }));
        }
      });
  };

  // メールアドレス変更
  const changeEmailApi = (email, password) => {
    setBanner({
      ...banner,
      banner: false,
    });
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
          value={email.value}
          onChange={(e) =>
            setEmail((v) => ({
              ...v,
              value: e.target.value,
              message: "",
              error: false,
            }))
          }
          fullWidth={true}
          inputProps={{
            style: {
              color: "#424242",
            },
          }}
          error={email.error}
          label={email.message}
        />
      </div>

      <div className="emailBox">
        <span>パスワード</span>
        <TextField
          variant="standard"
          type="password"
          autoComplete="off"
          value={password.value}
          onChange={(e) =>
            setPassword((v) => ({
              ...v,
              value: e.target.value,
              message: "",
              error: false,
            }))
          }
          fullWidth={true}
          error={password.error}
          label={password.message}
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
