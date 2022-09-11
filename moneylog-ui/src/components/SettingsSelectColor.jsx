import React from "react";
/** CSS */
import "./components_CSS/SettingsSelectColor.css";

const SettingsSelectColor = (props) => {
  /** カラーリスト */
  const { colorList } = props;

  /** 選択されているカラー */
  const selectedColor = "#D9AFD9, #97D9E1";

  /** カラー選択処理 */
  const selectColor = (colorCode) => {
    console.log(colorCode);
  };

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
                border: data == selectedColor ? "4px solid #2196f3" : "",
                transform: data == selectedColor ? "scale(1.2)" : "",
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
                background: "linear-gradient(" + data + ")",
                border: data == selectedColor ? "4px solid #2196f3" : "",
                transform: data == selectedColor ? "scale(1.2)" : "",
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
