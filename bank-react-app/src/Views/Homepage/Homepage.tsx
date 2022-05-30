import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { getUserDetails } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

export const Homepage: React.FC = () => {

    const profile = useSelector((state:RootState) => state.user);

    const dispatch: AppDispatch = useDispatch();

    const {username} = useParams();

    const navigator = useNavigate();

    useEffect(() => {
        if (!profile.user) {
            navigator("/login");
        }
    }, []);

    /*
    useEffect(() => {
        if (!username && !profile.user) {
        }
        console.log("Current App State", profile);
    }, [profile]);
    */

    return(
        <div>
            <Navbar />
            <br/>
            <div className="homepage">
                <h1>Welcome, {profile.user?.firstname} {profile.user?.lastname}</h1>
            </div>
        </div>
    )
}