import React from "react";
import "./components_CSS/UncategorizedSavingCard.css";
import EditIcon from "@mui/icons-material/Edit";

const UncategorizedSavingCard = (props) => {
  const { UncategorizedSavingAmount } = props;
  return (
    <div className="uncategorizedSavingCard">
      <span>未分類の貯金額:</span>
      <span>{"¥" + UncategorizedSavingAmount.toLocaleString()}</span>
      <EditIcon className="edit-icon" />
    </div>
  );
};
export default UncategorizedSavingCard;
