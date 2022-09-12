import React from "react";
import { useCookies } from "react-cookie";
/** CSS */
import "./components_CSS/SettingsSelectColor.css";

const SettingsSelectColor = (props) => {
  /** カラーリスト */
  const { colorList, themeColor, setThemeColor } = props;

  const [cookie, setCookie] = useCookies();

  /** カラー選択処理 */
  const selectColor = (colorCode) => {
    setThemeColor(colorCode);
    setCookie("themeColor", colorCode);
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  fetch(`${rootURI}/user/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: "sample@sample.com",
      password: "passworda",
    }),
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data.status);
      console.log("Settings");
    });

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
                background: data,
                border: data == themeColor ? "3px solid #2196f3" : "",
                transform: data == themeColor ? "scale(1.2)" : "",
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
                background: `linear-gradient(${data})`,
                border: data == themeColor ? "3px solid #2196f3" : "",
                transform: data == themeColor ? "scale(1.2)" : "",
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
