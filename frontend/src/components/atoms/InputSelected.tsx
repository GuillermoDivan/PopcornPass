import * as React from "react";
import { SelectChangeEvent } from "@mui/material/Select";
import { MenuItem, FormControl, Select, useTheme } from "@mui/material";
import { UseFormRegisterReturn } from "react-hook-form";

export interface IOptionsValue {
    value: string;
    name: string;
}

interface Props {
    register: UseFormRegisterReturn,
    optionsValue: IOptionsValue[] | false,
    listTo: string
}

const InputSelected: any = ({ register, optionsValue, listTo }: Props) => {
    const [selectedValue, setSelectedValue] = React.useState("empty")
    const { palette } = useTheme()
    const handleChange = (event: SelectChangeEvent) => setSelectedValue(event.target.value);


    return (
        <FormControl sx={{
            width: { xs: "48%", sm: 220 }, textTransform: "uppercase",
            "& fieldset": { borderColor: "rgba(255, 255, 255, 0.2)" },
            "&:hover fieldset": { borderColor: "rgba(255, 255, 255, 0.5)!important" }
        }}>
            <Select
                value={selectedValue}
                onChange={handleChange}
                color="warning"
                displayEmpty
                inputProps={{
                    ...register,
                    "aria-label": "Without label",
                    sx: { color: "var(--gray-color)", "&:hover": { color: "var(--gray-color)!important" } }
                }}
                sx={{ "& .MuiSelect-icon": { color: "var(--gray-color)" } }}

                MenuProps={{
                    PaperProps: {
                        sx: {
                            backgroundColor: "black",
                            color: "var(--gray-color)",
                            "& .MuiMenuItem-root": {
                                borderBottom: "solid 1px rgba(255, 255, 255, 0.15)",
                                backgroundColor: "inherit",
                                "&:hover": {
                                    color: "var(--white)",
                                    backgroundColor: "rgba(255, 255, 255, 0.1)",
                                },
                                "&.Mui-selected": { color: palette.warning.main, },
                                "&.Mui-disabled": { opacity: 0.75 },
                            },
                        },
                    },
                }}
            >
                <MenuItem value="empty" disabled>
                    <em>{listTo}</em>
                </MenuItem>
                {optionsValue && optionsValue.length > 0
                    ? optionsValue.map(({ value, name }, i) => (
                        <MenuItem key={i} value={value} children={name} />))
                    : <MenuItem disabled children="Cargando opciones..." />
                }
            </Select>
        </FormControl >
    );


}


export default InputSelected