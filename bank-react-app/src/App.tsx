import React from 'react';
import { HashRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import { AccountsPage } from './Views/AccountsPage/AccountsPage';
import { Homepage } from './Views/Homepage/Homepage';
import {LoginPage} from './Views/LoginPage/LoginPage';
import { ProfilePage } from './Views/ProfilePage/ProfilePage';

function App() {
  return (
    <HashRouter>
      <Routes >
        <Route path="*" element={<Navigate to="/login" replace />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/homepage" element={<Homepage />}/>
        <Route path="/profile" element={<ProfilePage />}/>
        <Route path="/accounts" element={<AccountsPage />}/>
      </Routes>
    </HashRouter>
  );
}

export default App;
