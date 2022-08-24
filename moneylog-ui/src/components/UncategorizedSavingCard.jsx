import React from "react";
import "./components_CSS/UncategorizedSavingCard.css";
import EditIcon from "@mui/icons-material/Edit";

const UncategorizedSavingCard = () => {
  return (
    <>
      <div className="UncategorizedSavingCard">
        <span>未分類の貯金額:</span>
        <span>¥ nnn,nnn,nnn</span>
        <EditIcon className="edit-icon" />
      </div>
    </>
  );
};
export default UncategorizedSavingCard;
