export default
{
    transform:
        {
            "^.+\\.(js|jsx|ts|tsx)$": "babel-jest"
        },
    setupFilesAfterEnv:
        [
            '<rootDir>/setupTests.js'],
    testEnvironment: "jest-environment-jsdom",
    moduleNameMapper:
        {
            "\\.(css|less|scss|sass)$": "identity-obj-proxy"
        }
};
