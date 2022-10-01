import React from "react";
import "../components_CSS/window_CSS/SignUpWindow.css";
import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import AccountCircle from "@mui/icons-material/AccountCircle";
import CloseIcon from "@mui/icons-material/Close";
import { Button, CircularProgress } from "@mui/material";
import { useState } from "react";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

const SignUpWindow = (props) => {
  const { setWindow, setBanner } = props;
  const [isLoading, setLoading] = useState(false);
  const [newAccount, setNewAccount] = useState({
    email: "sample@sample.com",
    password: "password223",
    checkPassword: "password223",
  });
  const [showPassword, setShowPassword] = useState({
    password: false,
    checkPassword: false,
  });

  const [labels, setLabels] = useState({
    email: { message: "メールアドレス", status: false },
    password: { message: "パスワード", status: false },
    checkPassword: { message: "パスワード再入力", status: false },
  });

  const closeWindow = () => {
    setWindow(false);
  };

  const signUp = () => {
    // 未入力チェック
    if (
      !newAccount.email ||
      !newAccount.password ||
      !newAccount.checkPassword
    ) {
      setLabels((label) => ({
        ...label,
        email: {
          message: !newAccount.email ? "未入力" : "メールアドレス",
          status: !newAccount.email,
        },
        password: {
          message: !newAccount.password ? "未入力" : "パスワード",
          status: !newAccount.password,
        },
        checkPassword: {
          message: !newAccount.checkPassword ? "未入力" : "パスワード再入力",
          status: !newAccount.checkPassword,
        },
      }));
      return;
    }

    // メールアドレス要件チェック
    const regex =
      /^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$/;
    if (!regex.test(newAccount.email)) {
      setLabels((label) => ({
        ...label,
        email: {
          message: "メールアドレスを入力してください",
          status: true,
        },
        password: {
          message: "パスワード",
          status: false,
        },
        checkPassword: {
          message: "パスワード再入力",
          status: false,
        },
      }));
      return;
    }
    // パスワード一致チェック
    if (newAccount.password !== newAccount.checkPassword) {
      setLabels((label) => ({
        ...label,
        email: {
          message: "メールアドレス",
          status: false,
        },
        password: { message: "パスワードが一致していません", status: true },
        checkPassword: {
          message: "パスワードが一致していません",
          status: true,
        },
      }));
      return;
    }
    // パスワード要件チェック
    const passwordRegex = /^(?=.*?[a-z])(?=.*?\d)[a-z\d]{8,32}$/i;
    if (!passwordRegex.test(newAccount.password)) {
      setLabels((label) => ({
        ...label,
        email: {
          message: "メールアドレス",
          status: false,
        },
        password: { message: "半角英数で8-32文字", status: true },
        checkPassword: {
          message: "半角英数で8-32文字",
          status: true,
        },
      }));
      return;
    }

    /** 登録API */
    const rootURI = "http://localhost:8080";
    fetch(`${rootURI}/user/registUser`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: newAccount.email,
        password: newAccount.password,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
          closeWindow();
          setBanner({
            banner: true,
            bannerMessage: data.message,
            bannerType: data.status,
          });
        } else {
          // 失敗
          console.log("通信結果");
          setLabels((label) => ({
            ...label,
            email: { message: data.message, status: true },
            password: { message: "パスワード", status: false },
            checkPassword: { message: "パスワード", status: false },
          }));
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <div className="signupWindow">
      <CloseIcon
        onClick={closeWindow}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />
      <h3 className="signupTitle">新規アカウント登録</h3>

      <TextField
        error={labels.email.status}
        id="input-with-icon-textfield"
        label={labels.email.message}
        fullWidth={true}
        value={newAccount.email}
        onChange={(e) =>
          setNewAccount({ ...newAccount, email: e.target.value })
        }
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <AccountCircle />
            </InputAdornment>
          ),
        }}
        sx={{ marginY: 2 }}
        variant="standard"
        autoComplete="off"
      />

      <TextField
        error={labels.password.status}
        id="standard-basic"
        variant="standard"
        autoComplete="off"
        label={labels.password.message}
        value={newAccount.password}
        type={showPassword.password ? "text" : "password"}
        onChange={(e) =>
          setNewAccount({ ...newAccount, password: e.target.value })
        }
        InputProps={{
          startAdornment: (
            <InputAdornment
              onClick={() =>
                setShowPassword({
                  ...showPassword,
                  password: !showPassword.password,
                })
              }
              className="showIcon"
              position="start"
            >
              {showPassword.password ? (
                <VisibilityOffIcon />
              ) : (
                <VisibilityIcon />
              )}
            </InputAdornment>
          ),
        }}
        sx={{ marginY: 2 }}
        fullWidth={true}
      />

      <TextField
        error={labels.checkPassword.status}
        id="standard-basic"
        variant="standard"
        autoComplete="off"
        label={labels.checkPassword.message}
        value={newAccount.checkPassword}
        type={showPassword.checkPassword ? "text" : "password"}
        onChange={(e) =>
          setNewAccount({ ...newAccount, checkPassword: e.target.value })
        }
        InputProps={{
          startAdornment: (
            <InputAdornment
              onClick={() =>
                setShowPassword({
                  ...showPassword,
                  checkPassword: !showPassword.checkPassword,
                })
              }
              className="showIcon"
              position="start"
            >
              {showPassword.checkPassword ? (
                <VisibilityOffIcon />
              ) : (
                <VisibilityIcon />
              )}
            </InputAdornment>
          ),
        }}
        sx={{ marginY: 2 }}
        fullWidth={true}
      />

      {/* 登録ボタン */}
      <Button
        onClick={signUp}
        variant="contained"
        disabled={isLoading}
        sx={{ marginY: 2 }}
      >
        {isLoading ? <CircularProgress size={20} /> : "登録"}
      </Button>
    </div>
  );
};

export default SignUpWindow;