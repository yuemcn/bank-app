import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IUser } from "../Interfaces/IUser";

interface CustomersSliceState {
    loading: boolean,
    error: boolean,
    users?: IUser[]
}

const initialCustomersState: CustomersSliceState = {
    loading: false,
    error: false
}

export const getAllUsers = createAsyncThunk(
    "user/all-users",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = await axios.get("http://localhost:8000/users/all-users");
            return res.data;
        } catch (e) {
            console.log(e);
        }
    }
)

// create slice

export const CustomersSliceState = createSlice({
    name: "customers",
    initialState: initialCustomersState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {
        builder.addCase(getAllUsers.fulfilled, (state, action) => {
            state.loading = false;
            state.error = false;
            state.users = action.payload;
        })
    }
})

export const {toggleError} = CustomersSliceState.actions;

export default CustomersSliceState.reducer;