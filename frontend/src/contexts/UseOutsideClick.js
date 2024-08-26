import { useCallback, useEffect, useRef } from 'react';

const useOutsideClick = (callback) => {
    const ref = useRef(null);

    const handleClick = useCallback(
        ({ target }) => {
            if (!ref.current || !target) return;

            if (ref.current.contains(target)) return;

            callback();
        },
        [callback],
    );

    useEffect(() => {
        document.addEventListener('click', handleClick);

        return () => {
            document.removeEventListener('click', handleClick);
        };
    });

    return [ref];
};

export default useOutsideClick;
