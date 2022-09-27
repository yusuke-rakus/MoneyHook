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
  const { setWindow } = props;
  const [isLoading, setLoading] = useState(false);
  const [newAccount, setNewAccount] = useState({
    email: "",
    password: "",
    checkPassword: "",
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
          message: !newAccount.email ? "未入力" : labels.email.message,
          status: !newAccount.email,
        },
        password: {
          message: !newAccount.password ? "未入力" : labels.password.message,
          status: !newAccount.password,
        },
        checkPassword: {
          message: !newAccount.checkPassword
            ? "未入力"
            : labels.checkPassword.message,
          status: !newAccount.checkPassword,
        },
      }));
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
      }));
    }
    // パスワード一致チェック
    if (newAccount.password !== newAccount.checkPassword) {
      setLabels((label) => ({
        ...label,
        password: { message: "パスワードが一致していません", status: true },
        checkPassword: {
          message: "パスワードが一致していません",
          status: true,
        },
      }));
    }
    // パスワード要件チェック
    const passwordRegex = /^(?=.*?[a-z])(?=.*?\d)[a-z\d]{8,32}$/i;
    if (!passwordRegex.test(newAccount.password)) {
      setLabels((label) => ({
        ...label,
        password: { message: "半角英数で8-32文字", status: true },
        checkPassword: {
          message: "半角英数で8-32文字",
          status: true,
        },
      }));
    }

    /** 登録API */
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
