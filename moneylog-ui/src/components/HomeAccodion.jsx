import React from "react";
/** CSS */
import "./components_CSS/HomeAccodion.css";
/** 外部コンポーネント */
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const HomeAccodion = (props) => {
  const { homeAccodionData, bgcolor } = props;

  return (
    <Accordion>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        aria-controls="panel1a-content"
        id="panel1a-header"
        sx={{
          bgcolor: bgcolor,
        }}
      >
        <Typography className="categoryGroup">
          <span className="text">{homeAccodionData.categoryName}</span>
          <span className="text">
            {`¥${Math.abs(
              homeAccodionData.categoryTotalAmount
            ).toLocaleString()}`}
          </span>
        </Typography>
      </AccordionSummary>
      {homeAccodionData.subCategoryList.map((subCategoryData, i) => {
        return (
          <AccordionDetails key={i}>
            <Typography className="subCategoryGroup">
              <span className="subCategoryData">
                <span>{subCategoryData.subCategoryName}</span>
                <span>
                  {`¥${Math.abs(
                    subCategoryData.subCategoryTotalAmount
                  ).toLocaleString()}`}
                </span>
              </span>
            </Typography>
          </AccordionDetails>
        );
      })}
    </Accordion>
  );
};
export default HomeAccodion;
