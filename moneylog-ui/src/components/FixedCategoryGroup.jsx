import React from "react";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./components_CSS/FixedCategoryGroup.css";

const FixedCategoryGroup = (props) => {
  const { fixedCategoryData } = props;

  return (
    <>
      {fixedCategoryData.map((data) => {
        return (
          <>
            <Accordion>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
              >
                <Typography
                  className={
                    data.categoryAmount > 0
                      ? "fixedIncomeCategoryGroup"
                      : "fixedSpendingCategoryGroup"
                  }
                >
                  <div
                    className={
                      data.categoryAmount > 0
                        ? "fixedIncomeCategoryData"
                        : "fixedSpendingCategoryData"
                    }
                  >
                    <span>{data.categoryName}</span>
                    <span>
                      {"¥ " + Math.abs(data.categoryAmount).toLocaleString()}
                    </span>
                  </div>
                </Typography>
              </AccordionSummary>
              {data.fixedTransactionData.map((transactionData) => {
                return (
                  <>
                    <AccordionDetails>
                      <Typography
                        className={
                          data.categoryAmount > 0
                            ? "fixedIncomeTransactionGroup"
                            : "fixedSpendingTransactionGroup"
                        }
                      >
                        <div
                          className={
                            data.categoryAmount > 0
                              ? "fixedIncomeTransactionData"
                              : "fixedSpendingTransactionData"
                          }
                        >
                          <span>{transactionData.transactionName}</span>
                          <span>
                            {"¥ " +
                              Math.abs(
                                transactionData.transactionAmount
                              ).toLocaleString()}
                          </span>
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
export default FixedCategoryGroup;
