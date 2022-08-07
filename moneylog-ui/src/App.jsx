import React from "react";
import "./App.css";
import Sidebar from "./components/Sidebar";
import HouseholdBudgetButton from "./components/HouseholdBudgetButton";
import ModalWindow from "./components/ModalWindow";
import SwitchBalanceButton from "./components/SwitchBalanceButton";
import CategoryWindow from "./components/CategoryWindow";
import AddSavingWindow from "./components/AddSavingWindow";
import BlurView from "./components/BlurView";
import AddTargetWindow from "./components/AddTargetWindow";
import Settings from "@mui/icons-material/Settings";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import SidebarData from "./components/SidebarData";
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
          <Route path="/variable" element={<MonthlyVariable />}></Route>
          <Route path="/fixed" element={<MonthlyFixed />}></Route>
          <Route path="/savingList" element={<SavingList />}></Route>
          <Route path="/amount" element={<TotalSaving />}></Route>
          <Route path="/settings" element={<SettingsPage />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
