import * as React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';

interface Props {
  options: string[] | number[];
  setValue: (value: string | number) => void;
}

const RadioButtonsGroup = ({ options, setValue }: Props) => {
  return (
    <FormControl>
      <RadioGroup row aria-labelledby="demo-row-radio-buttons-group-label" name="row-radio-buttons-group">
        {options.map((option: string | number, index) => (
          <FormControlLabel
            key={`${index}-${option}`}
            value={option}
            control={<Radio />}
            label={`${option} €`}
            onClick={() => setValue(option)}
          />
        ))}
      </RadioGroup>
    </FormControl>
  );
};

export default RadioButtonsGroup;
