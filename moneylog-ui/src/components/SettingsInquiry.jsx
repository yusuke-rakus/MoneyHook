import React, { useState } from "react";
import "./components_CSS/SettingsInquiry.css";
import { useCookies } from "react-cookie";
import { rootURI } from "../env/env";
import { SettingsFetchError } from "./FetchError";
import { Button, TextField } from "@mui/material";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import SendIcon from "@mui/icons-material/Send";

const SettingsInquiry = (props) => {
  const { banner, setBanner } = props;
  const [inquiryMessage, setInquiryMessage] = useState("");
  const [isLoading, setLoading] = useState(false);
  const [cookie] = useCookies();
  const [isCheck, setCheck] = useState(false);

  /** API関連 */
  /** チェック処理 */
  const checkInquiryMessage = () => {
    // メッセージ内容Nullチェック
    if (!inquiryMessage) {
      setBanner({
        ...banner,
        bannerMessage: "お問い合わせ内容を入力してください",
        bannerType: "error",
        banner: true,
      });
      return;
    }

    setLoading(true);
    setBanner({
      ...banner,
      banner: false,
    });
    fetch(`${rootURI}/user/checkInquiry`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === "success") {
          // 成功処理
          setCheck(true);
        } else {
          // 失敗処理
          setBanner({
            ...banner,
            bannerMessage: data.message,
            bannerType: data.status,
            banner: true,
          });
          setLoading(false);
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  /** 送信処理 */
  const sendInquiryMessage = () => {
    setLoading(false);
    fetch(`${rootURI}/user/sendInquiry`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        inquiry: inquiryMessage,
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
        setCheck(false);
        setInquiryMessage("");
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchError(setLoading, setBanner);
      });
  };

  return (
    <div className="containerBox">
      <p className="settingsTitle">ご意見・お問い合わせ</p>
      <hr className="border" />

      <div className="inquiryArea">
        <TextField
          value={inquiryMessage}
          onChange={(e) => setInquiryMessage(e.target.value)}
          className="inquiryTextfield"
          multiline
          rows={5}
          disabled={isLoading}
          sx={isLoading ? { backgroundColor: "#e0e0e0" } : {}}
        />
      </div>
      <div className="inquiryButton">
        <Button
          onClick={checkInquiryMessage}
          variant="contained"
          endIcon={<SendIcon sx={{ color: "#ffffff" }} />}
          disabled={isLoading}
        >
          確認
        </Button>
      </div>

      <Modal
        open={isCheck}
        onClose={() => {
          setCheck(false);
          setLoading(false);
        }}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: "80%",
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
            borderRadius: "5px",
          }}
        >
          <Typography variant="h6" component="h2" sx={{ color: "#424242" }}>
            1日1回のみ送信可能です。こちらの内容でよろしいでしょうか？
          </Typography>
          <Typography sx={{ mt: 2, color: "#424242" }}>
            {inquiryMessage}
          </Typography>

          <Button
            onClick={sendInquiryMessage}
            variant="contained"
            endIcon={<SendIcon sx={{ color: "#ffffff" }} />}
            disabled={!isLoading}
            sx={{ mt: 4 }}
          >
            送信
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

export default SettingsInquiry;
