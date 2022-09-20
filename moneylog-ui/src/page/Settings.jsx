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
import Sidebar from "../components/Sidebar";
import DeletedSavingTarget from "../components/DeletedSavingTarget";
import DeletedFixed from "../components/DeletedFixed";

const Settings = (props) => {
  const { colorList, setColorList, themeColor, setThemeColor } = props;

  return (
    <>
      <Sidebar themeColor={themeColor} />

      <div className="homeArea">
        <div className="container">
          {/* 固定費の編集 */}
          <SettingsFixedList />

          {/* ユーザー設定変更 */}
          <SettingsUserSettings />

          {/* パスワード変更 */}
          <SettingsChangePassword />

          {/* カラーの選択 */}
          <SettingsSelectColor
            colorList={colorList}
            setColorList={setColorList}
            themeColor={themeColor}
            setThemeColor={setThemeColor}
          />

          {/* 削除済み目標 */}
          <DeletedSavingTarget />

          {/* 削除済み固定費 */}
          <DeletedFixed />
        </div>
      </div>
    </>
  );
};
export default Settings;
