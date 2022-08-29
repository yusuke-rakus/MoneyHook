import React from "react";
import "./components_CSS/SettingsFixed.css";
import {
  TextField,
  Button,
  FormControl,
  IconButton,
  NativeSelect,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import SwitchBalanceButton from "../components/SwitchBalanceButton";
import { useState } from "react";

const SettingsFixed = (props) => {
  const { data } = props;

  return (
    <div className="fixedData">
      <div className="categoryData">
        <Button variant="text" sx={"color:#424242"}>
          住宅/家賃
        </Button>
      </div>
      <div className="transactionNameData">
        <span>取引名</span>
        <TextField id="standardBasic" variant="standard" />
      </div>
      <div className="fixedAmountData">
        <span>金額</span>
        <TextField id="standardBasic" variant="standard" />
      </div>
      <div className="transferDate">
        <span>振替日</span>
        <FormControl sx={{ minWidth: 60 }} size="small">
          <NativeSelect>
            <option value=""></option>
            <option value={1}>1</option>
            <option value={2}>2</option>
            <option value={3}>3</option>
          </NativeSelect>
        </FormControl>
        日
      </div>
      <div className="switchBalanceArea">
        <SwitchBalanceButton />
      </div>
      <div className="deleteArea">
        <IconButton aria-label="delete">
          <DeleteIcon />
        </IconButton>
      </div>
    </div>
  );
};

export default SettingsFixed;
