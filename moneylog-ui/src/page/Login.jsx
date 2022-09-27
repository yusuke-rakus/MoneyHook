import React from "react";
import { useState } from "react";
/** CSS */
import "./page_CSS/Login.css";
/** 自作コンポーネント */
import SignUpWindow from "../components/window/SignUpWindow";
import BlurView from "../components/window/BlurView";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import { CSSTransition } from "react-transition-group";
import {
  Alert,
  Button,
  CircularProgress,
  Collapse,
  IconButton,
  TextField,
} from "@mui/material";

const Login = () => {
  const [loginForm, setLoginForm] = useState({});
  const [isLoading, setLoading] = useState(false);
  const [banner, setBanner] = useState({
    banner: false,
    bannerMessage: "",
    bannerType: "success",
  });
  const [window, setWindow] = useState(false);

  /** API関連 */
  const rootURI = "http://localhost:8080";

  /** ログイン処理 */
  const login = () => {
    setBanner({ ...banner, banner: false });
    setLoading(true);
    fetch(`${rootURI}/user/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: loginForm.email,
        password: loginForm.password,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功
          setLoginForm({ ...loginForm, email: "", password: "" });
        } else {
          // 失敗
        }
        setBanner({
          ...banner,
          banner: true,
          bannerMessage: data.message,
          bannerType: data.status,
        });
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <div className="loginPage">
      <div className="inputWindow">
        <div className="inputArea">
          <TextField
            label="メールアドレス"
            variant="standard"
            autoComplete="off"
            fullWidth={true}
            value={loginForm.email}
            onChange={(e) => {
              setLoginForm({ ...loginForm, email: e.target.value });
            }}
          />
          <TextField
            label="パスワード"
            variant="standard"
            autoComplete="off"
            fullWidth={true}
            value={loginForm.password}
            onChange={(e) => {
              setLoginForm({ ...loginForm, password: e.target.value });
            }}
          />
        </div>

        {/* ログイン */}
        <Button
          onClick={login}
          variant="contained"
          disabled={isLoading}
          className="loginButton"
        >
          {isLoading ? <CircularProgress size={20} /> : "ログイン"}
        </Button>

        {/* 新規登録 */}
        <Button
          onClick={() => setWindow(true)}
          disabled={isLoading}
          sx={{ color: "#9e9e9e", marginTop: "20px" }}
        >
          新規登録
        </Button>
      </div>

      {/* バーナー */}
      <div className="banner">
        <Collapse in={banner.banner}>
          <Alert
            severity={banner.bannerType}
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setBanner({ ...banner, banner: false });
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
            sx={{
              mb: 1,
            }}
          >
            {banner.bannerMessage}
          </Alert>
        </Collapse>
      </div>

      {/* 取引追加画面 */}
      <BlurView status={window} setStatus={setWindow} />
      <CSSTransition
        in={window}
        timeout={200}
        unmountOnExit
        classNames="Modal-show"
      >
        <SignUpWindow setWindow={setWindow} />
      </CSSTransition>
    </div>
  );
};

export default Login;
