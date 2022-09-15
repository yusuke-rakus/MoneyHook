import React from "react";
import { useCookies } from "react-cookie";
/** CSS */
import "./components_CSS/SettingsSelectColor.css";

const SettingsSelectColor = (props) => {
  /** カラーリスト */
  const { colorList, setColorList, themeColor, setThemeColor } = props;

  const [cookie, setCookie] = useCookies();

  /** カラー選択処理 */
  const selectColor = (colorCode) => {
    setThemeColor(colorCode);
    setCookie("themeColor", colorCode);
    // editThemeColorApi(themeColorId入れる)
  };

  /** API関連 */
  const rootURI = "http://localhost:8080";

  // テーマカラーリストを取得
  const getInit = () => {
    fetch(`${rootURI}/user/getThemeColor`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          setColorList(data.themeColorList);
        }
      });
  };

  // テーマカラー変更
  const editThemeColorApi = (themeColorId) => {
    fetch(`${rootURI}/user/getThemeColor`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: "a77a6e94-6aa2-47ea-87dd-129f580fb669",
        themeColorId: themeColorId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "success") {
          // 成功処理
        } else {
          // 失敗処理
        }
      });
  };

  // useEffect(() => {
  //   getInit();
  // }, [setColorList]);

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
