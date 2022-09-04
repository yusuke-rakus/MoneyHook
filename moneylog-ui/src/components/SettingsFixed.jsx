import React from "react";
/** CSS */
import "./components_CSS/SettingsFixed.css";
/** 自作コンポーネント */
import SwitchBalanceButton from "../components/SwitchBalanceButton";
/** 外部コンポーネント */
import {
  TextField,
  Button,
  FormControl,
  IconButton,
  NativeSelect,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const SettingsFixed = (props) => {
  const { data } = props;

  return (
    <div className="fixedData">
      <div className="categoryData">
        <Button variant="text" sx={"color:#424242"}>
          {data.categoryName !== null
            ? data.categoryName + "/" + data.subCategoryName
            : "カテゴリ選択"}
        </Button>
      </div>
      <div className="transactionNameData">
        <span>取引名</span>
        <TextField
          id="standardBasic"
          variant="standard"
          value={data.monthlyTransactionName}
        />
      </div>
      <div className="fixedAmountData">
        <span>金額</span>
        <TextField
          id="standardBasic"
          variant="standard"
          value={Math.abs(data.monthlyTransactionAmount)}
        />
      </div>
      <div className="transferDate">
        <span>振替日</span>
        <FormControl sx={{ minWidth: 60 }} size="small">
          <NativeSelect
            defaultValue={data.monthlyTransactionDate}
            inputProps={{ name: "age", id: "uncontrolled-native" }}
          >
            <option value=""></option>
            <option value={1}>10</option>
            <option value={2}>20</option>
            <option value={3}>30</option>
            <option value={25}>25</option>
            <option value={30}>30</option>
          </NativeSelect>
        </FormControl>
        日
      </div>
      <div className="switchBalanceArea">
        <SwitchBalanceButton
          balance={data.monthlyTransactionAmount}
          id={data.monthlyTransactionId}
        />
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
