import React, { useState, useEffect } from "react";
/** CSS */
import "./page_CSS/ResetPassword.css";
/** 自作コンポーネント */
import { rootURI } from "../env/env";
/** 外部コンポーネント */
import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import AccountCircle from "@mui/icons-material/AccountCircle";
import { Button, CircularProgress } from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import { useLocation, useNavigate } from "react-router-dom";

const ResetPassword = () => {
  const [isLoading, setLoading] = useState(false);
  const [pageLoading, setPageLoading] = useState(true);
  const location = useLocation();
  const param = new URLSearchParams(location.search).get("param");
  const [labels, setLabels] = useState({
    email: { message: "メールアドレス", status: false },
    password: { message: "パスワード", status: false },
    checkPassword: { message: "パスワード再入力", status: false },
  });
  const [newAccount, setNewAccount] = useState({
    email: "",
    password: "",
    checkPassword: "",
  });
  const [showPassword, setShowPassword] = useState({
    password: false,
    checkPassword: false,
  });
  const navigate = useNavigate();

  /** API関連 */
  const getInit = () => {
    setLoading(true);
    fetch(`${rootURI}/user/resetPasswordPage`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        param: param,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          setNewAccount({ ...newAccount, email: data.email });
          setPageLoading(false);
        } else {
          // ログイン画面にリダイレクト
          navigate("/login");
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  /** パスワード再設定処理 */
  const resetPassword = () => {
    setLoading(true);
    // 未入力チェック
    if (
      !newAccount.email ||
      !newAccount.password ||
      !newAccount.checkPassword
    ) {
      setLabels((label) => ({
        ...label,
        password: {
          message: !newAccount.password ? "未入力" : "パスワード",
          status: !newAccount.password,
        },
        checkPassword: {
          message: !newAccount.checkPassword ? "未入力" : "パスワード再入力",
          status: !newAccount.checkPassword,
        },
      }));
      setLoading(false);
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
      setLoading(false);
      return;
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
      setLoading(false);
      return;
    }

    /** パスワードリセットAPI */
    fetch(`${rootURI}/user/forgotPasswordReset`, {
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
        if (data.status === "success") {
          // ログイン画面にリダイレクト
          navigate("/login");
        } else {
          // 失敗
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    getInit();
  }, [setNewAccount]);

  return (
    <div className="resetPasswordCard">
      {pageLoading ? (
        <CircularProgress size={20} />
      ) : (
        <>
          {/* メールアドレス */}
          <TextField
            disabled
            error={labels.email.status}
            id="input-with-icon-textfield"
            label={labels.email.message}
            fullWidth={true}
            value={newAccount.email}
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

          {/* パスワード */}
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

          {/* パスワード再入力 */}
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

          {/* 更新ボタン */}
          <Button
            onClick={resetPassword}
            variant="contained"
            disabled={isLoading}
            sx={{ marginY: 2 }}
          >
            {isLoading ? <CircularProgress size={20} /> : "更新"}
          </Button>
        </>
      )}
    </div>
  );
};

export default ResetPassword;
