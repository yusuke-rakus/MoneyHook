import React, { useEffect } from "react";
import { useCookies } from "react-cookie";
import { rootURI } from "../env/env";
/** CSS */
import "./components_CSS/SettingsSelectColor.css";
/** 自作コンポーネント */
import { SettingsFetchErrorOnlyBanner } from "./FetchError";

const SettingsSelectColor = (props) => {
  /** カラーリスト */
  const {
    banner,
    setBanner,
    colorList,
    setColorList,
    themeColor,
    setThemeColor,
  } = props;

  const [cookie, setCookie] = useCookies();

  /** カラー選択処理 */
  const selectColor = (colorData) => {
    let colorCode = !colorData.themeColorCode
      ? colorData.themeColorGradientCode
      : colorData.themeColorCode;
    editThemeColorApi(colorData.themeColorId);
    setThemeColor(colorCode);
    setCookie("themeColor", colorCode);
  };

  /** API関連 */
  const getInit = () => {
    // テーマカラーリストを取得
    fetch(`${rootURI}/user/getThemeColor`, {
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
          setColorList({
            ...colorList,
            themeColorGradientCodeList: data.themeColorList.filter(
              (data) => !data.themeColorCode
            ),
            themeColorCodeList: data.themeColorList.filter(
              (data) => !data.themeColorGradientCode
            ),
          });
        }
      })
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchErrorOnlyBanner(setBanner);
      });
  };

  // テーマカラー変更
  const editThemeColorApi = (themeColorId) => {
    setBanner({
      ...banner,
      banner: false,
    });
    fetch(`${rootURI}/user/editThemeColor`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: cookie.userId,
        themeColorId: themeColorId,
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
      .catch(() => {
        // サーバーエラーが発生した場合
        SettingsFetchErrorOnlyBanner(setBanner);
      });
  };

  useEffect(() => {
    getInit();
  }, [setColorList]);

  return (
    <div className="containerBox">
      <p className="settingsTitle">イメージカラーを選択</p>
      <hr className="border" />
      <div className="selectArea">
        {colorList.themeColorCodeList.map((data, i) => {
          return (
            <div
              key={i}
              onClick={() => selectColor(data)}
              style={{
                background: data.themeColorCode,
                border:
                  data.themeColorCode === themeColor ? "3px solid #2196f3" : "",
                transform:
                  data.themeColorCode === themeColor ? "scale(1.2)" : "",
              }}
              className="colorBox"
            ></div>
          );
        })}
      </div>
      <div className="selectArea">
        {colorList.themeColorGradientCodeList.map((data, i) => {
          return (
            <div
              key={i}
              onClick={() => selectColor(data)}
              style={{
                background: `linear-gradient(${data.themeColorGradientCode})`,
                border:
                  data.themeColorGradientCode === themeColor
                    ? "3px solid #2196f3"
                    : "",
                transform:
                  data.themeColorGradientCode === themeColor
                    ? "scale(1.2)"
                    : "",
              }}
              className="gradientColorBox"
            ></div>
          );
        })}
      </div>
    </div>
  );
};
export default SettingsSelectColor;
