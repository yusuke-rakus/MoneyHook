import React from "react";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./components_CSS/VariableCategoryGroup.css";
import VariableSubCategoryGroup from "../components/VariableSubCategoryGroup";

const VariableCategoryGroup = (props) => {
  const { variableCategoryData } = props;

  return (
    <>
      {variableCategoryData.map((data) => {
        return (
          <>
            <Accordion>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
              >
                <Typography className="variableCategoryGroup">
                  <div className="variableCategoryData">
                    <span>{data.categoryName}</span>
                    <span>
                      {"¥" + data.categoryTotalAmount.toLocaleString()}
                    </span>
                  </div>
                </Typography>
              </AccordionSummary>
              <VariableSubCategoryGroup
                variableSubCategoryData={data.subCategoryList}
              />
            </Accordion>
          </>
        );
      })}
    </>
  );
};
export default VariableCategoryGroup;
