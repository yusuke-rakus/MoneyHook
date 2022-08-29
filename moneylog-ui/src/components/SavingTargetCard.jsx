import React from "react";
import { Box } from "@mui/material";
import "./components_CSS/SavingTargetCard.css";

const SavingTargetCard = (props) => {
  const { savingTargetData } = props;

  return (
    <>
      <Box
        sx={{
          boxShadow: 2,
          minWidth: 320,
          width: "100%",
          height: 200,
          bgcolor: (theme) =>
            theme.palette.mode === "dark" ? "#101010" : "#fff",
          p: 1,
          m: 1,
          borderRadius: 2,
        }}
        className="card"
      >
        <p className="title">{savingTargetData.savingTargetName}</p>
        <hr />
        <div className="card-body">
          <table>
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
          </table>
          <div className="saving-amount-area">
            <span>貯金額</span>
            <p>{"¥" + savingTargetData.savingAmount.toLocaleString()}</p>
          </div>
        </div>
      </Box>
    </>
  );
};
export default SavingTargetCard;
