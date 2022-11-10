import React from "react";
import { useState } from "react";
import { useCookies } from "react-cookie";
import { rootURI } from "../env/env";
import { SettingsFetchError } from "./FetchError";
import { Button, CircularProgress, TextField } from "@mui/material";

const SettingsChangePassword = (props) => {
  const { banner, setBanner } = props;
  const [cookie] = useCookies();

  const [password, setPassword] = useState({
    currentPassword: { password: "", message: "", error: false },
    newPassword1: { password: "", message: "", error: false },
    newPassword2: { password: "", message: "", error: false },
  });

  const [isLoading, setLoading] = useState(false);

  /** キャンセル */
  const clear = () => {
    // 入力値をクリア
    setPassword((value) => ({
      ...value,
      currentPassword: { password: "", message: "", error: false },
      newPassword1: { password: "", message: "", error: false },
      newPassword2: { password: "", message: "", error: false },
    }));
  };

  /** API関連 */
  /** パスワード変更 */
  const changePasswordApi = () => {
    setBanner({
      ...banner,
      banner: false,
    });

    // パスワードチェック
    // 未入力
    if (
      !password.currentPassword.password ||
      !password.newPassword1.password ||
      !password.newPassword2.password
    ) {
      setPassword((value) => ({
        ...value,
        currentPassword: {
          password: password.currentPassword.password,
          message: !password.currentPassword.password && "未入力",
          error: !password.currentPassword.password,
        },
        newPassword1: {
          password: password.newPassword1.password,
          message: !password.newPassword1.password && "未入力",
          error: !password.newPassword1.password,
        },
        newPassword2: {
          password: password.newPassword2.password,
          message: !password.newPassword2.password && "未入力",
          error: !password.newPassword2.password,
        },
      }));
      return;
    }

    // 変更後パスワード一致チェック
    if (password.newPassword1.password !== password.newPassword2.password) {
      setPassword((value) => ({
        ...value,
        currentPassword: {
          password: password.currentPassword.password,
          message: "",
          error: false,
        },
        newPassword1: {
          password: password.newPassword1.password,
          message: "パスワードが一致しません",
          error: true,
        },
        newPassword2: {
          password: password.newPassword2.password,
          message: "パスワードが一致しません",
          error: true,
        },
      }));
      return;
    }

    // パスワード要件チェック
    const passwordRegex = /^(?=.*?[a-z])(?=.*?\d)[a-z\d]{8,32}$/i;
    if (!passwordRegex.test(password.newPassword1.password)) {
      setPassword((value) => ({
        ...value,
        currentPassword: {
          password: password.currentPassword.password,
          message: "",
          error: false,
        },
        newPassword1: {
          password: password.newPassword1.password,
          message: "半角英数で8-32文字",
          error: true,
        },
        newPassword2: {
          password: password.newPassword2.password,
          message: "半角英数で8-32文字",
          error: true,
        },
      }));
      return;
    }

    setLoading(true);

    fetch(`${rootURI}/user/changePassword`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        password: password.currentPassword.password,
        newPassword: password.newPassword1.password,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
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
        setPassword((value) => ({
          ...value,
          currentPassword: { password: "", message: "", error: false },
          newPassword1: { password: "", message: "", error: false },
          newPassword2: { password: "", message: "", error: false },
        }));
        setLoading(false);
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  return (
    <div className="containerBox">
      <p className="settingsTitle">パスワード変更</p>
      <hr className="border" />
      <div className="passwordBox">
        <span>現在のパスワード</span>
        <TextField
          value={password.currentPassword.password}
          onChange={(e) =>
            setPassword((value) => ({
              ...value,
              currentPassword: {
                password: e.target.value,
                message: password.currentPassword.message,
                error: password.currentPassword.error,
              },
            }))
          }
          variant="standard"
          type="password"
          fullWidth={true}
          error={password.currentPassword.error}
          label={password.currentPassword.message}
        />
      </div>
      <div className="passwordBox">
        <span>変更後のパスワード</span>
        <TextField
          value={password.newPassword1.password}
          onChange={(e) =>
            setPassword((value) => ({
              ...value,
              newPassword1: {
                password: e.target.value,
                message: password.newPassword1.message,
                error: password.newPassword1.error,
              },
            }))
          }
          variant="standard"
          type="password"
          fullWidth={true}
          error={password.newPassword1.error}
          label={password.newPassword1.message}
        />
      </div>
      <div className="passwordBox">
        <span>再入力</span>
        <TextField
          value={password.newPassword2.password}
          onChange={(e) =>
            setPassword((value) => ({
              ...value,
              newPassword2: {
                password: e.target.value,
                message: password.newPassword2.message,
                error: password.newPassword2.error,
              },
            }))
          }
          variant="standard"
          type="password"
          fullWidth={true}
          error={password.newPassword2.error}
          label={password.newPassword2.message}
        />
      </div>

      <div className="passwordSettingsButtons">
        <Button
          onClick={clear}
          variant="contained"
          color="inherit"
          disabled={isLoading}
        >
          クリア
        </Button>
        <Button
          onClick={changePasswordApi}
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
