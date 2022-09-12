import React from "react";
/** CSS */
import "./components_CSS/VariableSubCategoryGroup.css";
/** 外部コンポーネント */
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const VariableSubCategoryGroup = (props) => {
  const { variableSubCategoryData } = props;

  return (
    <>
      {variableSubCategoryData.map((data, i) => {
        return (
          <Accordion key={i}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1a-content"
              id="panel1a-header"
            >
              <Typography className="variableSubCategoryGroup">
                <span className="variableSubCategoryData">
                  <span>{data.subCategoryName}</span>
                  <span>
                    {`¥${Math.abs(
                      data.subCategoryTotalAmount
                    ).toLocaleString()}`}
                  </span>
                </span>
              </Typography>
            </AccordionSummary>
            {data.transactionList.map((transactionData, i) => {
              return (
                <AccordionDetails key={i}>
                  <Typography className="variableTransactionGroup">
                    <span className="variableTransactionData">
                      <span>{transactionData.transactionName}</span>
                      <span>
                        {`¥${Math.abs(
                          transactionData.transactionAmount
                        ).toLocaleString()}`}
                      </span>
                    </span>
                  </Typography>
                </AccordionDetails>
              );
            })}
          </Accordion>
        );
      })}
    </>
  );
};
export default VariableSubCategoryGroup;
