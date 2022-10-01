import React, { useState } from "react";
import "../components_CSS/window_CSS/ForgotPassword.css";
import CloseIcon from "@mui/icons-material/Close";
import {
  Button,
  CircularProgress,
  InputAdornment,
  TextField,
} from "@mui/material";
import { AccountCircle } from "@mui/icons-material";

const ForgotPassword = (props) => {
  const { setForgotPasswordWindow, setBanner } = props;
  const [isLoading, setLoading] = useState(false);
  const [userEmail, setUserEmail] = useState({
    value: "",
    message: "登録したメールアドレス",
    status: false,
  });

  const closeWindow = () => {
    setForgotPasswordWindow(false);
  };

  const sendEmail = () => {
    console.log(userEmail);
    // 未入力
    if (!userEmail.value) {
      setUserEmail({
        value: userEmail.value,
        message: "未入力",
        status: true,
      });
      return;
    }
    // メールアドレス要件チェック
    const regex =
      /^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$/;
    if (!regex.test(userEmail.value)) {
      setUserEmail({
        value: userEmail.value,
        message: "メールアドレスを入力してください",
        status: true,
      });
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
        email: userEmail.value,
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
          setUserEmail({
            value: userEmail.value,
            message: data.message,
            status: true,
          });
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <div className="forgotPasswordWindow">
      <CloseIcon
        onClick={closeWindow}
        style={{ cursor: "pointer", color: "#a9a9a9" }}
        className="close-button"
      />
      <h3 className="forgotPasswordTitle">パスワード再設定用メールを送信</h3>

      <TextField
        error={userEmail.status}
        id="input-with-icon-textfield"
        label={userEmail.message}
        fullWidth={true}
        value={userEmail.value}
        onChange={(e) =>
          setUserEmail({
            value: e.target.value,
            message: userEmail.message,
            status: userEmail.status,
          })
        }
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <AccountCircle />
            </InputAdornment>
          ),
        }}
        sx={{ marginY: 5 }}
        variant="standard"
        autoComplete="off"
      />

      {/* 登録ボタン */}
      <Button
        onClick={sendEmail}
        variant="contained"
        disabled={isLoading}
        sx={{ marginY: 2 }}
      >
        {isLoading ? <CircularProgress size={20} /> : "登録"}
      </Button>
    </div>
  );
};

export default ForgotPassword;