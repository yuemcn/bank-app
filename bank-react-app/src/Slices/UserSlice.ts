import {createSlice, createAsyncThunk} from "@reduxjs/toolkit"
import axios from "axios"
import { IUser } from "../Interfaces/IUser"

// Figure out our default state for this slice

interface UserSliceState {
    loading: boolean, // whenever we are loading the user
    error: boolean, // user was or was not able to log in
    user?: IUser,
    currentProfile?: IUser
}

const initialUserState: UserSliceState = {
    loading: false, // start out not loading anything
    error: false
}

type Login = {
    username: string,
    password: string
}

export const loginUser = createAsyncThunk(
    'user/login',
    async (credentials:Login, thunkAPI) => {
        try {
            const res = await axios.post('http://localhost:8000/users/login', credentials);
            return res.data;
        } catch (e) {
            return thunkAPI.rejectWithValue("something went wrong");
        }
    }
)

export const getUserDetails = createAsyncThunk(
    'users/get',
    async (username:string, thunkAPI) => {
        try {
            const res = await axios.get(`http://localhost:8000/users/${username}`);

            return {
                firstname: res.data.firstname,
                lastname: res.data.lastname,
                ssn: res.data.ssn,
                email: res.data.email,
                username: res.data.username,
                // password: string,
                type: res.data.type,
                // accounts?: IAccount[]
            }
        } catch(error) {
            console.log(error);
        }
    }
)

export const logout = createAsyncThunk(
    "user/logout",
    async (thunkAPI) => {
        try {
            axios.defaults.withCredentials = true;
            const res = axios.get("http://localhost:8000/users/logout");
        } catch (e) {
            console.log(e);
        }
    }
)

// create the slice

export const UserSlice = createSlice({
    name: "user", // name user because that is what we are storing inside the slice
    initialState: initialUserState,
    reducers: {
        toggleError: (state) => {
            state.error = !state.error;
        }
    },
    extraReducers: (builder) => {
        // This is where we would create our reducer logic
        builder.addCase(loginUser.pending, (state, action) => {
            // whenever our thunk is pending, do this logic
            state.loading = true;
        });
        builder.addCase(loginUser.fulfilled, (state, action) => {
            // the payload in this case is the return from our asyncThunk from above
            state.user = action.payload;
            state.error = false;
            state.loading = false;
        });
        builder.addCase(loginUser.rejected, (state, action) => {
            // what happens if the request is not successful; done loading but issue with login
            state.error = true;
            state.loading = false;
        });
    }
})

// If we had normal actions and reducers, we would export them like this
export const {toggleError} = UserSlice.actions;

export default UserSlice.reducer;