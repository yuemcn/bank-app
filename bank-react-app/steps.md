# Setting Up Environment

## Start With Blank Slate

### Create blank webpage

- clear div contents in App.tsx
- make divs into fragments
- clear everything in App.css
- remove logo from App.tsx
- delete logo.svg

### Remove web vitals

- remove web vitals from index.tsx
- delete reportWebVitals.ts file

### Start the Application

- `npm start`

## Style

### Choose Color Palette

- choose a color palette from a color palette generator or custom palette
- create colors.txt in main directory to store colors
- index.css:
    - create root


# Setup Login

## Make folders

- Components folder
- Views folder

## Components Folder

### Create LoginForm folder

- create index.tsx
    - write body for return
    - create empty methods for handleInput and handleLogin

## Views Folder

### Create LoginPage folder

- create LoginPage.tsx
    - write body for return
    - import file in App.tsx

# Edit Appearance

## Syle App

- edit App.css file
- clear out index.css file

## Style Login Page

- create LoginPage.css under Views\LoginPage
- import LoginPage.css in LoginPage.tsx
- write css for LoginPage.css
- create LoginForm.css file in Components\LoginForm

# Add States to Login

# Set up a centralized Store

- Store the user state to access it anywhere

- make a new file called Store.ts in main src directory
- install `npm i @reduxjs/toolkit react-redux redux react-router-dom`
- start out store with an empty reducer

# Create Reducers

- create Slices folder
- create UserSlice.ts file

## Make Interfaces folder

- make interfaces for IUser and IPost

Left off at: -1:23:08