import React from "react";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./components_CSS/VariableSubCategoryGroup.css";

const VariableSubCategoryGroup = (props) => {
  const { variableSubCategoryData } = props;

  return (
    <>
      {variableSubCategoryData.map((data) => {
        return (
          <>
            <Accordion>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
              >
                <Typography className="variableSubCategoryGroup">
                  <div className="variableSubCategoryData">
                    <span>{data.subCategoryName}</span>
                    <span>{data.subCategoryAmount}</span>
                  </div>
                </Typography>
              </AccordionSummary>
              {data.transactionList.map((transactionData) => {
                return (
                  <>
                    <AccordionDetails>
                      <Typography className="variableTransactionGroup">
                        <div className="variableTransactionData">
                          <span>{transactionData.transactionName}</span>
                          <span>{transactionData.transactionAmount}</span>
                        </div>
                      </Typography>
                    </AccordionDetails>
                  </>
                );
              })}
            </Accordion>
          </>
        );
      })}
    </>
  );
};
export default VariableSubCategoryGroup;
