import React, { useState } from "react";
/** CSS */
import "../components_CSS/window_CSS/ForgotPassword.css";
/** 外部コンポーネント */
import CloseIcon from "@mui/icons-material/Close";
import {
  Button,
  CircularProgress,
  InputAdornment,
  TextField,
} from "@mui/material";
import { AccountCircle } from "@mui/icons-material";
import { rootURI } from "../../App";

const ForgotPassword = (props) => {
  const { setForgotPasswordWindow, banner, setBanner } = props;
  const [isLoading, setLoading] = useState(false);
  const [userEmail, setUserEmail] = useState({
    value: "sample@sample.com",
    message: "登録したメールアドレス",
    status: false,
  });

  const closeWindow = () => {
    setForgotPasswordWindow(false);
  };

  const sendEmail = () => {
    setLoading(true);
    setBanner({
      banner: false,
    });
    // 未入力
    if (!userEmail.value) {
      setUserEmail({
        value: userEmail.value,
        message: "未入力",
        status: true,
      });
      setLoading(false);
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
      setLoading(false);
      return;
    }

    /** 登録API */
    fetch(`${rootURI}/user/forgotPasswordSendEmail`, {
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
        closeWindow();
        setBanner({
          banner: true,
          bannerMessage: data.message,
          bannerType: data.status,
        });
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
