(() => {
    const SELECTED_CLASS = "is-selected";
    const ITEM_SELECTOR = "[data-nav-item]:not([disabled])";

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

        if (commandItem) {
            commandItem.click();
            return true;
        }

        return false;
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
            return;
        }

        if (window.history.length > 1) {
            window.history.back();
        }
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

        switch (event.key) {
            case "ArrowUp":
                event.preventDefault();

                if (!activateMapCommand("UP")) {
                    moveSelection(-1);
                }

                break;
            case "ArrowDown":
                event.preventDefault();

                if (!activateMapCommand("DOWN")) {
                    moveSelection(1);
                }

                break;
            case "ArrowLeft":
                event.preventDefault();

                if (!activateMapCommand("LEFT")) {
                    moveSelection(-1);
                }

                break;
            case "ArrowRight":
                event.preventDefault();

                if (!activateMapCommand("RIGHT")) {
                    moveSelection(1);
                }

                break;
            case "Enter":
            case " ":
                event.preventDefault();
                activateItem(items[getSelectedIndex(items)]);
                break;
            case "Escape":
            case "Backspace":
                event.preventDefault();
                back();
                break;
            default:
                break;
        }
    });
})();