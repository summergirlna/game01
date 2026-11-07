(() => {
    const SELECTED_CLASS = "is-selected";
    const ITEM_SELECTOR = "[data-nav-item]:not([disabled])";

    const MAP_COMMAND_BY_KEY = {
        ArrowUp: "UP",
        ArrowDown: "DOWN",
        ArrowLeft: "LEFT",
        ArrowRight: "RIGHT"
    };

    const SELECTION_MOVE_BY_KEY = {
        ArrowUp: -1,
        ArrowDown: 1,
        ArrowLeft: -1,
        ArrowRight: 1
    };

    const DECISION_KEYS = new Set(["Enter", " "]);
    const BACK_KEYS = new Set(["Escape", "Backspace"]);

    const getItems = () => Array.from(document.querySelectorAll(ITEM_SELECTOR))
        .filter((item) => item.offsetParent !== null);

    const getSelectedIndex = (items) => {
        const currentIndex = items.findIndex((item) => item.classList.contains(SELECTED_CLASS));
        return currentIndex >= 0 ? currentIndex : 0;
    };

    const selectItem = (items, index) => {
        if (items.length === 0) {
            return;
        }

        items.forEach((item) => {
            item.classList.remove(SELECTED_CLASS);
            item.classList.remove("active");
        });

        const nextIndex = (index + items.length) % items.length;
        const nextItem = items[nextIndex];

        nextItem.classList.add(SELECTED_CLASS);
        nextItem.focus({preventScroll: true});
    };

    const activateItem = (item) => {
        if (!item) {
            return;
        }

        item.click();
    };

    const activateMapCommand = (command) => {
        const commandItem = document.querySelector(`[data-map-command="${command}"]`);

        if (!commandItem) {
            return false;
        }

        commandItem.click();
        return true;
    };

    const moveSelection = (direction) => {
        const items = getItems();

        if (items.length === 0) {
            return;
        }

        const currentIndex = getSelectedIndex(items);
        selectItem(items, currentIndex + direction);
    };

    const back = () => {
        const backItem = document.querySelector("[data-nav-back]");

        if (backItem) {
            backItem.click();
        }
    };

    const handleArrowKey = (key) => {
        const mapCommand = MAP_COMMAND_BY_KEY[key];

        if (activateMapCommand(mapCommand)) {
            return;
        }

        moveSelection(SELECTION_MOVE_BY_KEY[key]);
    };

    window.addEventListener("DOMContentLoaded", () => {
        const items = getItems();

        if (items.length > 0) {
            selectItem(items, getSelectedIndex(items));
        }
    });

    window.addEventListener("keydown", (event) => {
        const items = getItems();

        if (items.length === 0) {
            return;
        }

        if (event.key in SELECTION_MOVE_BY_KEY) {
            event.preventDefault();
            handleArrowKey(event.key);
            return;
        }

        if (DECISION_KEYS.has(event.key)) {
            event.preventDefault();
            activateItem(items[getSelectedIndex(items)]);
            return;
        }

        if (BACK_KEYS.has(event.key)) {
            event.preventDefault();
            back();
        }
    });
})();