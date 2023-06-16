import React, { useEffect, useState } from "react";
/** CSS */
import "./page_CSS/Settings.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SettingsSelectColor from "../components/SettingsSelectColor";
import SettingsInquiry from "../components/SettingsInquiry";
import SettingsUserSettings from "../components/SettingsUserSettings";
import SettingsChangePassword from "../components/SettingsChangePassword";
import SettingsFixedList from "../components/SettingsFixedList";
import Sidebar from "../components/Sidebar";
import DeletedSavingTarget from "../components/DeletedSavingTarget";
import DeletedFixed from "../components/DeletedFixed";
/** 外部コンポーネント */
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import { timeoutRange } from "../env/env";

const Settings = (props) => {
  const { colorList, setColorList, themeColor, setThemeColor } = props;
  const navigate = useNavigate();
  const [cookie, setCookie, deleteCookie] = useCookies();
  /** バナーのステータス */
  const [banner, setBanner] = useState({
    banner: false,
    bannerMessage: "",
    bannerType: "",
  });

  useEffect(() => {
    setTimeout(() => {
      console.log("実行");
      setBanner({
        ...banner,
        banner: false,
      });
    }, timeoutRange);
  }, [banner.banner]);

  const logout = () => {
    // ユーザーIDを削除
    deleteCookie("userId");
    // ホーム画面にリダイレクト
    navigate("/login");
  };

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
        <div className="container">
          {/* 固定費の編集 */}
          <SettingsFixedList banner={banner} setBanner={setBanner} />

          {/* カラーの選択 */}
          <SettingsSelectColor
            colorList={colorList}
            setColorList={setColorList}
            themeColor={themeColor}
            setThemeColor={setThemeColor}
            banner={banner}
            setBanner={setBanner}
          />

          {/* 削除済み目標 */}
          <DeletedSavingTarget banner={banner} setBanner={setBanner} />

          {/* 削除済み固定費 */}
          <DeletedFixed banner={banner} setBanner={setBanner} />

          {/* 問い合わせ・ご意見 */}
          <SettingsInquiry banner={banner} setBanner={setBanner} />

          {/* ユーザー設定変更 */}
          <SettingsUserSettings banner={banner} setBanner={setBanner} />

          {/* パスワード変更 */}
          <SettingsChangePassword banner={banner} setBanner={setBanner} />

          {/* ログアウト */}
          <div className="logout">
            <Button onClick={logout} sx={{ marginTop: 4, color: "#9e9e9e" }}>
              ログアウト
            </Button>
          </div>

          {/* バーナー */}
          <div className="bannerArea">
            <Collapse in={banner.banner}>
              <Alert
                severity={banner.bannerType}
                action={
                  <IconButton
                    aria-label="close"
                    color="inherit"
                    size="small"
                    onClick={() => {
                      setBanner({
                        ...banner,
                        banner: false,
                      });
                    }}
                  >
                    <CloseIcon fontSize="inherit" />
                  </IconButton>
                }
                sx={{ mb: 1 }}
              >
                {banner.bannerMessage}
              </Alert>
            </Collapse>
          </div>
        </div>
      </div>
    </>
  );
};
export default Settings;
