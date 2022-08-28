import React from "react";
import "./App.css";
import Sidebar from "./components/Sidebar";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Home from "./page/Home";
import Timeline from "./page/Timeline";
import MonthlyVariable from "./page/MonthlyVariable";
import MonthlyFixed from "./page/MonthlyFixed";
import SavingList from "./page/SavingList";
import TotalSaving from "./page/TotalSaving";
import SettingsPage from "./page/Settings";

const App = () => {
  return (
    <BrowserRouter>
      <Sidebar />
      <div className="homeArea">
        {/* <HouseholdBudgetButton BoxText={"追加"} /> */}
        {/* <ModalWindow /> */}
        {/* <AddSavingWindow /> */}
        {/* <AddTargetWindow /> */}

        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/home" element={<Home />}></Route>
          <Route path="/timeline" element={<Timeline />}></Route>
          <Route path="/monthlyVariable" element={<MonthlyVariable />}></Route>
          <Route path="/monthlyFixed" element={<MonthlyFixed />}></Route>
          <Route path="/savingList" element={<SavingList />}></Route>
          <Route path="/totalSaving" element={<TotalSaving />}></Route>
          <Route path="/settingsPage" element={<SettingsPage />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
