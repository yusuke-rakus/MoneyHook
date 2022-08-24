import React from "react";
import SavingTargetCard from "../components/SavingTargetCard";
import UncategorizedSavingCard from "../components/UncategorizedSavingCard";
import "./page_CSS/TotalSaving.css";
import "./page_CSS/common.css";
import AddSharpIcon from "@mui/icons-material/AddSharp";

const TotalSaving = () => {
  return (
    <>
      <div className="SavingTargetCardArea">
        <SavingTargetCard />
        <SavingTargetCard />
        <AddSharpIcon className="AddSavingTargetButton" />
      </div>

      <UncategorizedSavingCard className="UncategorizedSavingCard" />
    </>
  );
};
export default TotalSaving;
