import React from "react";
/** CSS */
import "./components_CSS/FixedCategoryGroup.css";
/** 外部コンポーネント */
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const FixedCategoryGroup = (props) => {
  const { fixedCategoryData } = props;

  return (
    <>
      {fixedCategoryData.map((data, i) => {
        return (
          <Accordion key={i}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1a-content"
              id="panel1a-header"
            >
              <Typography
                className={
                  data.totalCategoryAmount > 0
                    ? "fixedIncomeCategoryGroup"
                    : "fixedSpendingCategoryGroup"
                }
              >
                <span
                  className={
                    data.totalCategoryAmount > 0
                      ? "fixedIncomeCategoryData"
                      : "fixedSpendingCategoryData"
                  }
                >
                  <span>{data.categoryName}</span>
                  <span>
                    {`¥ ${Math.abs(data.totalCategoryAmount).toLocaleString()}`}
                  </span>
                </span>
              </Typography>
            </AccordionSummary>
            {data.transactionList.map((transactionData, i) => {
              return (
                <AccordionDetails key={i}>
                  <Typography
                    className={
                      data.totalCategoryAmount > 0
                        ? "fixedIncomeTransactionGroup"
                        : "fixedSpendingTransactionGroup"
                    }
                  >
                    <span
                      className={
                        data.totalCategoryAmount > 0
                          ? "fixedIncomeTransactionData"
                          : "fixedSpendingTransactionData"
                      }
                    >
                      <span>{transactionData.transactionName}</span>
                      <span>
                        {`¥ ${Math.abs(
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
export default FixedCategoryGroup;
