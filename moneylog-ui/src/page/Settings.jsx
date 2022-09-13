import React from "react";
/** CSS */
import "./page_CSS/Settings.css";
import "./page_CSS/common.css";
/** 自作コンポーネント */
import SettingsSelectColor from "../components/SettingsSelectColor";
/** 外部コンポーネント */
import SettingsUserSettings from "../components/SettingsUserSettings";
import SettingsChangePassword from "../components/SettingsChangePassword";
import SettingsFixedList from "../components/SettingsFixedList";

const Settings = (props) => {
  const { colorList, themeColor, setThemeColor } = props;

  return (
    <div className="container">
      {/* ユーザー設定変更 */}
      <SettingsUserSettings />

      {/* パスワード変更 */}
      <SettingsChangePassword />

      {/* 固定費の編集 */}
      <SettingsFixedList />

      {/* カラーの選択 */}
      <SettingsSelectColor
        colorList={colorList}
        themeColor={themeColor}
        setThemeColor={setThemeColor}
      />
    </div>
  );
};
export default Settings;
