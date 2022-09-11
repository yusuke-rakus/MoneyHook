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

const App = () => {
  const colorList = {
    themeColorCodeList: [
      "#76d5ff",
      "#607d8b",
      "#212121",
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
  };

  const [cookie, setCookie] = useCookies();

  const [themeColor, setThemeColor] = useState(
    cookie.themeColor === void 0 ? "#76d5ff" : cookie.themeColor
  );

  return (
    <BrowserRouter>
      <Sidebar themeColor={themeColor} />
      <div className="homeArea">
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/home" element={<Home />}></Route>
          <Route path="/timeline" element={<Timeline />}></Route>
          <Route path="/monthlyVariable" element={<MonthlyVariable />}></Route>
          <Route path="/monthlyFixed" element={<MonthlyFixed />}></Route>
          <Route path="/savingList" element={<SavingList />}></Route>
          <Route path="/totalSaving" element={<TotalSaving />}></Route>
          <Route
            path="/settingsPage"
            element={
              <SettingsPage
                colorList={colorList}
                themeColor={themeColor}
                setThemeColor={setThemeColor}
              />
            }
          ></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
