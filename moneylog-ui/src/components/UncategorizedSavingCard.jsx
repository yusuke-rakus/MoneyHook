import React from "react";
/** CSS */
import "./components_CSS/UncategorizedSavingCard.css";
/** 外部コンポーネント */
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
