import React, { useState } from "react";
/** CSS */
import "./App.css";
/** 自作コンポーネント */
import Sidebar from "./components/Sidebar";
import Home from "./page/Home";
import Timeline from "./page/Timeline";
import MonthlyVariable from "./page/MonthlyVariable";
import MonthlyFixed from "./page/MonthlyFixed";
import SavingList from "./page/SavingList";
import TotalSaving from "./page/TotalSaving";
import SettingsPage from "./page/Settings";
/** 外部コンポーネント */
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { useCookies } from "react-cookie";
import NotFound from "./page/NotFound";

const App = () => {
  /** 背景色のリスト */
  const [colorList, setColorList] = useState({
    themeColorCodeList: [
      "#76d5ff",
      "#607d8b",
      "#424242",
      "#43a047",
      "#00acc1",
      "#e53935",
      "#8e24aa",
    ],
    themeColorGradientCodeList: [
      "#355C7D, #C06C84",
      "#11998e, #38ef7d",
      "#108dc7, #ef8e38",
      "#FC5C7D, #6A82FB",
      "#74ebd5, #ACB6E5",
      "#36D1DC, #5B86E5",
      "#D9AFD9, #97D9E1",
    ],
  });

  const [cookie, setCookie] = useCookies();

  const [themeColor, setThemeColor] = useState(
    cookie.themeColor === void 0 ? "#76d5ff" : cookie.themeColor
  );

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home themeColor={themeColor} />}></Route>
        <Route path="/home" element={<Home themeColor={themeColor} />}></Route>
        <Route
          path="/timeline"
          element={<Timeline themeColor={themeColor} />}
        ></Route>
        <Route
          path="/monthlyVariable"
          element={<MonthlyVariable themeColor={themeColor} />}
        ></Route>
        <Route
          path="/monthlyFixed"
          element={<MonthlyFixed themeColor={themeColor} />}
        ></Route>
        <Route
          path="/savingList"
          element={<SavingList themeColor={themeColor} />}
        ></Route>
        <Route
          path="/totalSaving"
          element={<TotalSaving themeColor={themeColor} />}
        ></Route>
        <Route
          path="/settingsPage"
          element={
            <SettingsPage
              colorList={colorList}
              setColorList={setColorList}
              themeColor={themeColor}
              setThemeColor={setThemeColor}
            />
          }
        ></Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
