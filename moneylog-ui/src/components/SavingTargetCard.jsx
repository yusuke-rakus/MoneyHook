import React from "react";
/** CSS */
import "./components_CSS/SavingTargetCard.css";
/** 外部コンポーネント */
import { Box } from "@mui/material";

const SavingTargetCard = (props) => {
  const {
    savingTargetData,
    setWindowStatus,
    setTitle,
    setEditSavingTarget,
  } = props;

  const editSavingTarget = () => {
    setTitle("貯金目標を編集");
    setWindowStatus(true);
    setEditSavingTarget(savingTargetData);
  };

  return (
    <div
      onClick={() => {
        editSavingTarget();
      }}
      className="card"
    >
      <Box
        sx={{
          boxShadow: 2,
          minWidth: 320,
          width: "100%",
          height: 200,
          bgcolor: (theme) =>
            theme.palette.mode === "dark" ? "#101010" : "#fff",
          p: 2,
          m: 1,
          borderRadius: 2,
        }}
      >
        <p className="title">{savingTargetData.savingTargetName}</p>
        <hr />
        <div className="card-body">
          <table>
            <tbody>
              <tr>
                <td>目標</td>
                <td>{savingTargetData.targetAmount.toLocaleString()}</td>
                <td>円</td>
              </tr>
              <tr>
                <td>貯金回数</td>
                <td>{savingTargetData.savingCount}</td>
                <td>回</td>
              </tr>
            </tbody>
          </table>
          <div className="saving-amount-area">
            <span>貯金額</span>
            <p>
              {savingTargetData.totalSavedAmount === void 0
                ? 0
                : `¥ ${savingTargetData.totalSavedAmount.toLocaleString()}`}
            </p>
          </div>
        </div>
      </Box>
    </div>
  );
};
export default SavingTargetCard;
